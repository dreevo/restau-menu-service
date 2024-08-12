# Build
custom_build(
     # Name of the container image
     ref = 'menu-service',
     tag = 'latest',
     # Command to build the container image
     command = './gradlew bootBuildImage --imageName menu-service:latest',
     # Files to watch that trigger a new build
     deps = ['build.gradle', 'src']
)
# Deploy
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])
# Manage
k8s_resource('menu-service', port_forwards=['9001'])