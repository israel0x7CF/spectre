from flask import Blueprint,jsonify,request

from InstanceManager.routes.utils.instanceModuels import get_modules_on_containers

test = Blueprint('test', __name__)

@test.route("/test",methods=["get"])
def test_instance():
    get_modules_on_containers()