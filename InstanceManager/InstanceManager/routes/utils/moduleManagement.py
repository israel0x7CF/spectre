import pathlib
import shutil
import os
import odoorpc
import argparse

from InstanceManager.routes.utils.restart_container import container_restart


def prepare_args():
    """Prepare arguments for module action RPC call."""
    parser = argparse.ArgumentParser(
        description="Run modules install, upgrade or uninstall."
    )
    parser.add_argument(
        '-i',
        '--install',
        help="Comma separated list of modules to install",
    )
    parser.add_argument(
        '-u',
        '--upgrade',
        help="Comma separated list of modules to upgrade",
    )
    parser.add_argument(
        '-del',
        '--delete',
        help="Comma separated list of modules to uninstall",
    )
    parser.add_argument(
        '--user',
        help="User to log in with",

    )
    parser.add_argument(
        '--password',
        help="Password to log in with",

    )
    parser.add_argument(
        '--host',
        help="Host to log in to",

    )
    parser.add_argument(
        '--port',
        help="Odoo port",

    )
    parser.add_argument(
        '-d',
        '--database',
        help="Database name to log in to",

    )
    return parser.parse_args()

def login(user, password, host, port, database):
    """Login to Odoo database and return connection object."""
    odoo = odoorpc.ODOO(host, port=port)
    odoo.login(database, user, password)
    return odoo


def _find_modules(env, module_names):
    IrModuleModule = env['ir.module.module']
    modules = module_names.replace(' ', '').split(',')
    module_ids = IrModuleModule.search([('name', 'in', modules)])
    return IrModuleModule.browse(module_ids)


def trigger_action(env, module_names, action):
    modules = _find_modules(env, module_names)
    method = getattr(modules, f'button_immediate_{action}')
    return method()

def move_module_to_instance_dir(instance_path, module_dir):
    try:
        # Check if the destination directory already exists
        dest = os.path.join(instance_path, os.path.basename(module_dir))  # Destination path

        # If it exists, remove or rename it (depending on your use case)
        if os.path.exists(dest):
            print(f"Directory '{dest}' already exists. Attempting to delete...")
            shutil.rmtree(dest)  # Delete the existing directory if you want to overwrite
            print(f"Deleted existing directory '{dest}'")

        # Now copy the module to the destination
        shutil.copytree(module_dir, dest)
        print(f"Module successfully copied to {dest}")
        return True

    except Exception as e:
        print(f"Error while moving module: {e}")
        return False


def install_to_instance(instance_user,instance_password,instance_host,instance_port,instance_db,module_name,is_active):
    print("---------------installing module-----------------------")
    try:
        args = prepare_args()
        print(instance_db,instance_password,instance_host,instance_user,instance_port)
        # if is_active:
        #     #container already exists we are adding new module
        #
        #     pass
        odoo = login(instance_user, instance_password, instance_host, instance_port, instance_db)
        env = odoo.env
        print(env)
        module_model = odoo.env['ir.module.module']
        module_id = module_model.search([('name', '=', module_name)])
        print(module_id)
        if module_id:
            module_model.browse(module_id).button_immediate_install()
            print(f"Module '{module_name}' has been successfully installed.")
        else:
            print(f"Module '{module_name}' not found.")

        if args.install:
            trigger_action(env, args.install, 'install')
        if args.upgrade:
            trigger_action(env, args.upgrade, 'upgrade')
        if args.delete:
            trigger_action(env, args.delete, 'uninstall')

        response_data = {
            "status": 200,
            "message": "Success",
            "data": {"module":"installed"}
        }
        return response_data
    except Exception as e:
        print(e)
        response_data = {
            "status": 500,
            "message":"install error",
            "data": None
        }
        return response_data

def install_to_running_instance(existing_module,container_id,instance_username,instance_password,instance_host,instance_port,instance_db,module_name):
    if not existing_module:
        #module does not exist in the instances dir,
        #move module to instance dir restart then install restart instance again

        pass
    ## if module exists in the instance dir then install it and restart it

    try:
        install_status = install_to_instance(instance_username,instance_password,instance_host,instance_port,instance_db,module_name,False)
    except Exception as e:
        print(e)
    if install_status.get("status") == 200:
        container_restart(container_id)






