terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "2.16.0"
    }
  }
}

resource "digitalocean_container_registry" "basic_registry" {
  for_each               = toset(var.registry_params)
  name                   = each.key
  subscription_tier_slug = "basic"
}

resource "digitalocean_container_registry_docker_credentials" "basic_registry_k8s_credentials" {
  for_each      = digitalocean_container_registry.basic_registry
  registry_name = each.value.name
}
