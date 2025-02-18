import requests
from termcolor import colored
from typing import Dict, List, Optional

ACTUATOR_URL = "http://localhost:8080/actuator/mappings"


def fetch_endpoints() -> Optional[Dict[str, List[str]]]:
    try:
        response = requests.get(ACTUATOR_URL)
        response.raise_for_status()
        return response.json()
    except requests.RequestException as e:
        print(colored(f"Error fetching endpoints: {e}", "red"))
        return None


def parse_endpoints(data: dict) -> Dict[str, List[str]]:
    endpoints = {}

    if not data or "contexts" not in data:
        print(colored("No valid endpoint data found. Verify:", "yellow"))
        print(
            colored(
                "1. Actuator is enabled (management.endpoints.web.exposure.include=mappings)",
                "yellow",
            )
        )
        print(colored("2. Application is running on port 8080", "yellow"))
        return endpoints

    for context in data["contexts"].values():
        for endpoint in (
            context.get("mappings", {})
            .get("dispatcherServlets", {})
            .get("dispatcherServlet", [])
        ):
            details = endpoint.get("details", {})
            handler_method = details.get("handlerMethod", {})

            # Improved module detection from class name
            class_name = handler_method.get("className", "")
            if ".modules." not in class_name:
                continue

            module = class_name.split(".modules.")[-1].split(".")[0].title()

            # Get HTTP methods
            methods = details.get("requestMappingConditions", {}).get(
                "methods", ["UNKNOWN"]
            )
            methods = [m.upper() for m in methods]

            # Get path patterns (excluding error endpoint)
            patterns = [
                p
                for p in details.get("requestMappingConditions", {}).get("patterns", [])
                if p not in ["/error"]
            ]

            if not patterns:
                continue

            if module not in endpoints:
                endpoints[module] = []

            for method in methods:
                for pattern in patterns:
                    endpoints[module].append(f"{method.ljust(6)} {pattern}")

    return endpoints


def print_endpoints(endpoints: Dict[str, List[str]]) -> None:
    if not endpoints:
        print(colored("\nNo endpoints detected. Possible issues:", "red"))
        print(colored("- Authentication required for Actuator endpoint", "red"))
        print(colored("- Application not running or wrong port", "red"))
        print(
            colored(
                "- Missing management.endpoints.web.exposure.include=mappings", "red"
            )
        )
        return

    print(colored("\nAPI Endpoints Documentation\n", "green", attrs=["bold"]))

    # Color scheme configuration
    method_colors = {
        "GET": "green",
        "POST": "yellow",
        "PUT": "cyan",
        "DELETE": "red",
        "PATCH": "magenta",
        "DEFAULT": "white",
    }
    for module, routes in sorted(endpoints.items()):
        print(colored(f"=== {module} ===", "cyan", attrs=["bold"]))
        for route in sorted(set(routes)):
            method, path = route.split(" ", 1)
            color = method_colors.get(method.strip().upper(), method_colors["DEFAULT"])

            # Color both method and path with the same color
            colored_method = colored(method.ljust(6), color)
            colored_path = colored(path, color)

            print(f"  {colored_method} {colored_path}")
        print()


if __name__ == "__main__":
    data = fetch_endpoints()
    if data:
        endpoints = parse_endpoints(data)
        print_endpoints(endpoints)
