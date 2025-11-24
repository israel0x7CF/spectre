
from flask import Blueprint,jsonify,request


from InstanceManager.routes.utils.moduleManagement import install_to_instance, install_to_running_instance

containerManager = Blueprint('containerManager', __name__)

@containerManager.route("/installModule",methods=["post"])
def instance_manager():
    module_info = request.get_json()
    instance_user = module_info.get("user")
    instance_password = module_info.get("password")
    instance_host = module_info.get("host")
    instance_port = module_info.get("port")
    instance_db = module_info.get("db")
    installation_module = module_info.get("module")
    is_active_instance = module_info.get("isActive")
    installation_repsonse=install_to_instance(instance_user,instance_password,instance_host,instance_port,instance_db,installation_module,is_active=False)
    return jsonify(installation_repsonse)

@containerManager.route("/running",methods=["post"])
def running_instance_manager():
    module_info = request.get_json()
    instance_user = module_info.get("user")
    instance_password = module_info.get("password")
    instance_host = module_info.get("host")
    instance_port = module_info.get("port")
    instance_db = module_info.get("db")
    installation_module = module_info.get("module")
    is_active_instance = module_info.get("isActive")
    existing_module = True
    container_id = "da6bf4f94dc8"


    if is_active_instance:
        try:
            if is_active_instance:
                installation_response = install_to_running_instance(
                    existing_module=existing_module,
                    container_id=container_id,
                    instance_username=instance_user,
                    instance_password=instance_password,
                    instance_host=instance_host,
                    instance_port=instance_port,
                    instance_db=instance_db,
                    module_name=installation_module,

                )
                return jsonify(installation_response)
        except Exception as e:
            print(e)
            return  None


