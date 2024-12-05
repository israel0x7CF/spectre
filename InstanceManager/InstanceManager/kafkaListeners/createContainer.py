import json
import threading
from confluent_kafka import Consumer,KafkaError
from flask import Flask
from InstanceManager.kafkaListeners.kafkaconf import  conf
from InstanceManager.kafkaProducer.responseProducer import sendResponse
from InstanceManager.routes.utils.container_creation import create_container
import concurrent.futures
listener_started_event = threading.Event()
app = Flask(__name__)
max_workers = 10  # Adjust based on your system's capability
executor = concurrent.futures.ThreadPoolExecutor(max_workers=max_workers)


def kafka_container_listener():
    print("starting kafka listener")
    create_container_topic = 'create_instance'
    consumer = Consumer({
    'bootstrap.servers': 'localhost:9092',  # Your Kafka broker
    'group.id': 'container_manager_group',
    'auto.offset.reset': 'earliest',

})
    print(f"Successfully subscribed to topic: {create_container_topic}")
    listener_started_event.set()  # Signal that the listener has started
    consumer.subscribe([create_container_topic])

    try:
        while True:
            msg = consumer.poll(5.0)
            if msg is None:
                continue
            if msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    print(f"Reached end of partition {msg.partition()}")
                else:
                    raise KafkaError(msg.error())
            else:
                try:
                    msg_value = msg.value().decode('utf-8')
                    data_json = json.loads(msg_value)
                    instance_name  = data_json["instanceName"]
                    module_name = data_json['moduleName']
                    module_path = data_json['modulePath']

                    future = executor.submit(create_container, instance_name,module_name,module_path)
                    def executor_callback(fut ):
                        try:
                            result = future.result()
                            print("creationResult",result)
                            sendResponse(result)
                        except Exception as exception:
                            print(exception)
                    future.add_done_callback(executor_callback)
                except Exception as e:
                    print(e)



    except Exception as e:
        print(e)
    finally:
        consumer.close()


def start_kafka_listner():
    kafka_thread = threading.Thread(target=kafka_container_listener)
    kafka_thread.daemon = True  # Daemon thread will exit when the main program exits
    kafka_thread.start()
    listener_started_event.wait()
    print("Kafka listener has started and is ready to receive messages.")
