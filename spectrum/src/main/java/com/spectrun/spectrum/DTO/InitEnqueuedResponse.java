package com.spectrun.spectrum.DTO;

import java.util.UUID;

public class InitEnqueuedResponse {
    private final long jobId;
    private final String status;
    public InitEnqueuedResponse(long jobId, String status) {
        this.jobId = jobId;
        this.status = status;
    }
    public long getJobId() { return jobId; }
    public String getStatus() { return status; }
}