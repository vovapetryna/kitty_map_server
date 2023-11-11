variable "registry_params" {
  description = "settings of container registry"
  type        = list(string)
}

variable "environment" {
  description = "namespace where to create secret"
  type        = string
}
