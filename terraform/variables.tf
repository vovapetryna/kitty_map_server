variable "do_token" {
  description = "Digital Ocean cli token used to deploy infrastructure"
  type        = string
}

variable "org_name" {
  description = "Common organization name (Used to constructs infrastructure names)"
  type        = string
}

variable "environment" {
  description = "Environment name"
  type        = string
}

variable "region" {
  description = "Default region deploy to (Frankfurt by default)"
  type        = string
}

variable "cluster_params" {
  description = "environment cluster settings"
  type        = object({
    cluster_name = string
    version      = string
    size         = string
    node_count   = number
  })
}

variable "registry_params" {
  description = "settings of container registry"
  type        = list(string)
}

