from flask import Flask

from InstanceManager.routes.containerManagement.create_container import containerManager
def create_app():
    app = Flask(__name__)
    app.register_blueprint(containerManager,url_prefix="/api/v1/containerManager")
    return app