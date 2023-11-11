variable "k8s_cluster_name" {
  description = "cluster name to witch grant read only docker access"
  type        = string
}

variable "environment" {
  description = "namespace where to create secret"
  type        = string
}

variable "registry" {
  type = map(object({
    docker_credentials = string,
    registry_name      = string
  }))
}