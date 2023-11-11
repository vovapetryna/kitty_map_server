do_token    = "dop_v1_92fcd26b3e4d77ff1ef79a40bdb10146d2bbca6833eec4c9317fab9fd968949f"
org_name    = "kitty"
region      = "sfo3"
environment = "prod"

cluster_params = {
  cluster_name = "kitty-k8s-cluster"
  version      = "1.28.2-do.0"
  size         = "s-4vcpu-8gb"
  node_count   = 1
}

registry_params = ["kitty-org"]