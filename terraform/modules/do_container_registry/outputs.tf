output "do_registry" {
  description = "created container registry"
  value       = digitalocean_container_registry.basic_registry
}

output "do_registry_credentials" {
  description = "created container registry credentials"
  value       = digitalocean_container_registry_docker_credentials.basic_registry_k8s_credentials
}