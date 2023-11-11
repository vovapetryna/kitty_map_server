terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "2.16.0"
    }
  }
}

resource "digitalocean_kubernetes_cluster" "k8s" {
  name         = var.cluster_params.cluster_name
  region       = var.do_region
  auto_upgrade = true
  version      = var.cluster_params.version

  tags = [var.cluster_params.cluster_name]

  node_pool {
    name       = "worker-pool-2"
    size       = var.cluster_params.size
    node_count = var.cluster_params.node_count
    tags       = [var.cluster_params.cluster_name]
  }

  maintenance_policy {
    start_time = "04:00"
    day        = "sunday"
  }
}
