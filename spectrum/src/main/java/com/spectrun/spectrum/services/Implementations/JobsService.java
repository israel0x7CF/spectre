package com.spectrun.spectrum.services.Implementations;

import com.spectrun.spectrum.Enums.JobStatus;
import com.spectrun.spectrum.models.Jobs;
import com.spectrun.spectrum.repositories.JobsRepository;

import jakarta.transaction.Transactional;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Objects;

@Service
public class JobsService {
  private JobsRepository jobsRepository;

  public JobsService(JobsRepository jobsRepository) {
    this.jobsRepository = jobsRepository;
  }

  public Jobs CreatePendingJob(String idempotencyKey){
    Objects.requireNonNull(idempotencyKey,"idempotencyKey");
    return  jobsRepository.findByIdempotencyKey(idempotencyKey).orElseGet(
            ()->{
              Jobs j= new Jobs();
              j.setIdempotencyKey(idempotencyKey);
              j.setStatus(JobStatus.PENDING);
              j.setCreatedAt(Instant.now());
              j.setUpdatedAt(j.getCreatedAt());
              return  jobsRepository.save(j);
            }
            //todo: build callback url
    );
  }
  public Jobs getJobById(long id){
      return  this.jobsRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
  }

  @Transactional
  public void updateJobStatus(long jobId,JobStatus status,String message,boolean strict){
      Jobs j = getJobById(jobId);
      JobStatus old = j.getStatus();
      if(strict){
        if(!(old == JobStatus.PENDING && status == JobStatus.RUNNING)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Illegal transition: " + old + " -> " + status);
        }
      }else {
        boolean ok = (status == JobStatus.PENDING && old == JobStatus.RUNNING) || (status == JobStatus.SUCCEEDED || status == JobStatus.FAILED);

          if (!ok) {
              throw new ResponseStatusException(HttpStatus.CONFLICT,
                      "Illegal transition: " + old + " -> " + status);
          }
          j.setStatus(status);
          j.setMessage(message);
          j.setUpdatedAt(Instant.now());

          try {
              this.jobsRepository.save(j);
          } catch (OptimisticLockingFailureException e) {
              // Rare race: caller can retry once if needed
              throw new ResponseStatusException(HttpStatus.CONFLICT, "Job update conflict, retry", e);
          }
      }

  }

  @Transactional
  public void markJobAsFailed(long jobId,String errorMessage){
      updateJobStatus(jobId,JobStatus.FAILED,errorMessage,false);
  }
    @Transactional
    public void markSucceeded(long jobId, String message) {
        updateJobStatus(jobId, JobStatus.SUCCEEDED, message, /*strict*/false);
    }
    @Transactional
    public void markRunning(long jobId) {
        updateJobStatus(jobId, JobStatus.RUNNING, null, /*strict*/true);
    }
}
