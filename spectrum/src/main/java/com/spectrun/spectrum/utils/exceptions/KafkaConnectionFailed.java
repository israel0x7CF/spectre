package com.spectrun.spectrum.utils.exceptions;

public class KafkaConnectionFailed extends  RuntimeException{
    public  KafkaConnectionFailed(String message){
        super(message);
    }
}
