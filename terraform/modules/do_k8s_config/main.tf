terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "2.16.0"
    }
  }
}

data "digitalocean_kubernetes_cluster" do_cluster {
  name = var.k8s_cluster_name
}

provider "kubernetes" {
  host                   = data.digitalocean_kubernetes_cluster.do_cluster.endpoint
  token                  = data.digitalocean_kubernetes_cluster.do_cluster.kube_config[0].token
  cluster_ca_certificate = base64decode(
    data.digitalocean_kubernetes_cluster.do_cluster.kube_config[0].cluster_ca_certificate
  )
}

#resource "kubernetes_namespace" "namespace" {
#  metadata {
#    name = var.environment
#  }
#}

resource "kubernetes_secret" "basic_registry_readonly_secrets" {
  for_each = var.registry

  metadata {
    name      = "docker-${each.value.registry_name}-cfg"
    namespace = var.environment
  }
  data = {
    ".dockerconfigjson" = each.value.docker_credentials
  }
  type = "kubernetes.io/dockerconfigjson"
}
