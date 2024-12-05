import subprocess
def container_restart():
    try:
        container_name="cc93dc02f177"
        # Run the docker restart command
        result = subprocess.run(
            ["/usr/bin/docker", "restart", container_name],
            check=True,  # Ensures an exception is raised on non-zero exit
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True  # Decodes stdout and stderr to strings
        )

        # Output success message
        print(f"Container '{container_name}' restarted successfully.")
        print("Output:", result.stdout)

    except subprocess.CalledProcessError as e:
        # Handle errors in subprocess execution
        print(f"Error restarting container '{container_name}': {e.stderr}")
    except FileNotFoundError:
        print("Docker is not installed or not found in PATH.")

