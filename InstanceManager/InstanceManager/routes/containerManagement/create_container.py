
from flask import Blueprint,jsonify,request


from InstanceManager.routes.utils.moduleManagement import install_to_instance

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
    installation_repsonse=install_to_instance(instance_user,instance_password,instance_host,instance_port,instance_db,installation_module)
    return jsonify(installation_repsonse)