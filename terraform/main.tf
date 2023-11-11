terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "2.16.0"
    }
  }
}

provider "digitalocean" {
  token = var.do_token
}

module "do_k8s" {
  source         = "./modules/do_kubernetes_kluster"
  cluster_params = var.cluster_params
  do_region      = var.region
}

module "do_registry" {
  source          = "./modules/do_container_registry"
  registry_params = var.registry_params
  environment     = var.environment
}

resource "digitalocean_project" "project" {
  depends_on  = [module.do_k8s]
  name        = var.org_name
  description = "production project of kitty project"
  environment = "Production"
  resources   = concat(module.do_k8s.all_resources_urns)
}

module "do_k8s_config" {
  source           = "./modules/do_k8s_config"
  k8s_cluster_name = var.cluster_params.cluster_name
  environment      = var.environment
  registry         = module.do_registry.do_registry_credentials
}
