import json
from gc import callbacks

from confluent_kafka import Producer
import socket

conf = {'bootstrap.servers': 'localhost:9092',
        'client.id': socket.gethostname()}

producer = Producer(conf)

def acked(err, msg):
    if err is not None:
        print("Failed to deliver message: %s: %s" % (str(msg), str(err)))
    else:
        print("Message produced: %s" % msg)
def sendResponse(response):
    print(f"sending response {response}")
    producer.produce(topic="create_response",value=json.dumps(response).encode("utf-8"),callback=acked)
    producer.flush()
