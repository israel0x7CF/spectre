import os
from time import sleep
from InstanceManager.routes.utils.configure_admin_access import configure_odoo_database
from InstanceManager.routes.utils.InstanceManager import docker_installation
import yaml
from InstanceManager.routes.utils.activePort import PortManager
from InstanceManager.routes.utils.copy_dir import create_new_config_dir
from InstanceManager.routes.utils.moduleManagement import move_module_to_instance_dir


def create_container(container_instance_name,module_name,module_path):
    print(f'.................starting create container:{container_instance_name}..........')
    instance_data = create_instance_config(container_instance_name)
    print(instance_data)
    #move files here
    status = move_module_to_instance_dir(instance_data.get("custom_addons_path"),module_path)
    docker_installation(instance_data['configurationFileLocation'])
    db_url = 'http://localhost:'+str(instance_data['instanceDbAdress'])
    instance_url = 'http://localhost:'+str(instance_data['instanceAddress'])
    sleep(5)
    config_status=configure_odoo_database(instanceUrl='http://localhost',instance_port=instance_data['instanceAddress'],odoo_instance_url=instance_url,database_name=instance_data['db_name'])
    instance_data["instanceCreationStatus"] = config_status["status"]
    instance_data["adminUserName"] = config_status["username"]
    instance_data["adminPassword"] = config_status["password"]
    instance_data["instanceDbAdress"] = db_url
    instance_data["instanceAddress"] = instance_url
    if config_status["status"]:
        instance_data["status"] = "Active"
    return  instance_data


def create_instance_config(container_instance_name):
    instance_config_dir = '/opt/container_configs/'
    instance_information = {}
    instance_port = PortManager().get_open_tcp_ports()[1]
    db_port = PortManager().get_open_tcp_ports()[1]
    container_port_mapping = [f'{instance_port}:8069']
    db_port_mapping = [f'{db_port}:5432']
    instance_name = container_instance_name
    instance_dir_path = os.path.join(instance_config_dir, instance_name + '/')
    instance_dir = create_new_config_dir(instance_dir_path)
    yaml_file_path = instance_dir + 'docker-compose.yml'

    # Open the existing docker-compose.yml file to edit
    with open(yaml_file_path) as file:
        print('starting file config------')
        base_config = yaml.safe_load(file)

    # Update the existing configuration
    base_config['services']['web']['ports'] = container_port_mapping
    base_config['services']['web']['container_name'] = instance_name
    base_config['services']['db']['container_name'] = instance_name + '_db'
    base_config['services']['db']['ports'] = db_port_mapping

    # Save the updated configuration back to the same file
    with open(yaml_file_path, 'w') as file:
        yaml.safe_dump(base_config, file, default_flow_style=False)

    # Update instance information
    instance_information['db_name'] = base_config['services']['db']['container_name']
    instance_information['instanceName'] = instance_name
    instance_information['instanceAddress'] = container_port_mapping
    instance_information['instanceDbAdress'] = db_port
    instance_information['configurationFileLocation'] = yaml_file_path
    instance_information['instanceAddress'] = instance_port
    instance_information["custom_addons_path"] = instance_dir_path+"addons"
    print(f"Configuration for instance '{instance_name}' updated at '{yaml_file_path}'")
    return instance_information
