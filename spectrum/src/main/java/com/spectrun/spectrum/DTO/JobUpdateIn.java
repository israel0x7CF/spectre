package com.spectrun.spectrum.DTO;

import com.spectrun.spectrum.Enums.JobStatus;

import java.util.Map;


public class JobUpdateIn {
    JobStatus status;    // RUNNING | QUEUED | SUCCEEDED | FAILED | CANCELLED | TIMED_OUT
    int progress;          // 0..100, optional
    String code;               // machine-readable phase/error code (e.g., "PKG_INSTALL")
    String message;            // human-readable note
    Map<String, Object> data;   // arbitrary metadata (ip, logs, step info)

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
