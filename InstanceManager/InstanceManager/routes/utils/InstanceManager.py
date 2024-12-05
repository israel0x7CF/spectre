import subprocess
import os
from operator import truediv

def docker_installation(compose_file_path):
    try:

        print(compose_file_path)
        compose_dir = os.path.dirname(compose_file_path)
        print(compose_dir)
        os.chdir(compose_dir)
        result = subprocess.run(["docker-compose","up","-d"],check=True,stdout=subprocess.PIPE,stderr=subprocess.PIPE, text=True)
        
    except Exception as e:
        print('instance Error')
        print(e)
