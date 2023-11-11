variable "cluster_params" {
  description = "environment cluster settings"
  type        = object({
    cluster_name = string
    version      = string
    size         = string
    node_count   = number
  })
}

variable "do_region" {
  description = "name of digital ocean region"
  type        = string
}