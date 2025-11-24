import subprocess
def container_restart(container_id):
    try:
        # Run the docker restart command
        result = subprocess.run(
            ["/usr/bin/docker", "restart", container_id],
            check=True,  # Ensures an exception is raised on non-zero exit
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True  # Decodes stdout and stderr to strings
        )

        # Output success message
        print(f"Container '{container_id}' restarted successfully.")
        print("Output:", result.stdout)

    except subprocess.CalledProcessError as e:
        # Handle errors in subprocess execution
        print(f"Error restarting container '{container_id}': {e.stderr}")
    except FileNotFoundError:
        print("Docker is not installed or not found in PATH.")

