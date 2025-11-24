from flask import Blueprint,jsonify,request
from InstanceManager.routes.utils.instanceModuels import get_modules_on_containers

module_installation_manager = Blueprint("modules",__name__)

@module_installation_manager.route("/",methods=["post"])
def instance_manager():
    instance_info = request.get_json()
    print(instance_info)
    username = instance_info.get("username")
    password = instance_info.get("password")
    port = int(instance_info.get("port"))
    address = instance_info.get("address")
    db_name = instance_info.get("dbName")

    result = get_modules_on_containers(username=username,password=password,port=port,address=address,instance_database=db_name)
    print(result)
    if result:
        response = {
            "status":200,
            "message":"fetch successful",
            "data":result

        }
        return jsonify(response)
    response = {
        "status": 400,
        "message": "fetch Faild",
        "data": None

    }
    return jsonify(response)