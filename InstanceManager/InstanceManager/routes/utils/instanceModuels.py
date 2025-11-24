import odoorpc


def get_modules_on_containers(username:str,password:str,port:int,address:str,instance_database:str):
    odoo = odoorpc.ODOO(address,protocol="jsonrpc",port=port)
    odoo.login(instance_database,username,password)

    module = odoo.env["ir.module.module"]
    installed_modules =module.search_read(
    [('state', '=', 'installed')], ['name', 'state']
    )
    uninstalled_modules = module.search_read(
        [('state', '!=', 'installed')], ['name', 'state']
    )
    module_status = {"installed": installed_modules, "uninstalled": uninstalled_modules}
    return module_status

