#!/bin/bash
set -euo pipefail
umask 077

command -v aws >/dev/null 2>&1 || {
  echo "AWS CLI is missing; cannot fetch the Origin key." >&2
  exit 1
}

mkdir -p /etc/pki/tls/certs /etc/pki/tls/private
install -m 644 "$PWD/origin.pem" /etc/pki/tls/certs/origin.crt

key_tmp="$(mktemp /etc/pki/tls/private/origin.key.XXXXXX)"
trap 'rm -f "$key_tmp"' EXIT

aws ssm get-parameter --region us-east-2 --name /recipeforge/origin-key --with-decryption --query 'Parameter.Value' --output text > "$key_tmp"

chmod 600 "$key_tmp"
mv "$key_tmp" /etc/pki/tls/private/origin.key
trap - EXIT