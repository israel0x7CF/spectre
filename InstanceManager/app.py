import json
import threading

from confluent_kafka import Consumer, KafkaError

from InstanceManager import create_app
from InstanceManager.kafkaListeners.createContainer import start_kafka_listner
from InstanceManager.kafkaListeners.kafkaconf import conf
from flask import Flask

app = create_app()

def run_kafka_listener():
    start_kafka_listner()

if __name__ == '__main__':
    # Start Kafka listener in a separate daemon thread
    kafka_thread = threading.Thread(target=run_kafka_listener)
    kafka_thread.daemon = True
    kafka_thread.start()

    # Start Flask app in the main thread
    app.run(host="0.0.0.0", port=5050,debug=True)
