output "k8s_connection" {
  sensitive = true
  value     = {
    endpoint    = module.do_k8s.do_cluster.endpoint
    kube_config = module.do_k8s.do_cluster.kube_config
  }
}

output "registry_usage" {
  sensitive = true
  value     = {
    name = module.do_registry.do_registry
  }
}