import socket
class PortManager:
    def get_open_tcp_ports(self):
        tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        tcp.bind(('', 0))
        return tcp.getsockname()