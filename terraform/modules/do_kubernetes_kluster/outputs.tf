output "do_cluster" {
  description = "created kubernetes cluster"
  value       = digitalocean_kubernetes_cluster.k8s
}

output "all_resources_urns" {
  description = "list of all created resources identifiers"
  value       = [digitalocean_kubernetes_cluster.k8s.urn]
}