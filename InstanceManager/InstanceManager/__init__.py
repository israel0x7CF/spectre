from flask import Flask

from InstanceManager.routes.containerManagement.create_container import containerManager
from InstanceManager.routes.containerManagement.module_information import module_installation_manager


def create_app():
    app = Flask(__name__)
    app.register_blueprint(containerManager,url_prefix="/api/v1/containerManager")
    app.register_blueprint(module_installation_manager,url_prefix="/api/v1/modules")
    return app