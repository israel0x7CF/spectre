import shutil

advanced_config_dir = '/home/isrishx/Documents/projects/python/InstanceManager/InstanceManager/advancedConfiguration'

def create_new_config_dir(instance_dir):
    new_path = shutil.copytree(advanced_config_dir,instance_dir)
    return  new_path