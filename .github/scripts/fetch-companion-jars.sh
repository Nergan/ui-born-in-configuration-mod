#!/usr/bin/env bash
# Скачивает из Modrinth игровые jar обязательных зависимостей.
# Берём только сборку NeoForge: у части модов один version_number
# бывает и у Forge, и у NeoForge, а API отдаёт Forge первым.
# Аргументы: <каталог> <версия KFF> <версия Born in Chaos> <версия Born in Configuration> <версия GeckoLib>
set -euo pipefail

OUT_DIR="${1:?destination directory}"
KFF_VERSION="${2:?kotlin-for-forge version_number}"
CHAOS_VERSION="${3:?born in chaos version_number}"
BIC_VERSION="${4:?born in configuration version_number}"
GECKOLIB_VERSION="${5:?geckolib version_number}"
USER_AGENT="${MODRINTH_USER_AGENT:-Nergan/ui-born-in-configuration-mod (https://github.com/Nergan/ui-born-in-configuration-mod)}"
LOADER="neoforge"

mkdir -p "${OUT_DIR}"

fetch_modrinth() {
  local slug="$1"
  local version="$2"
  local json url filename sha512

  json="$(curl -fsSL -A "${USER_AGENT}" "https://api.modrinth.com/v2/project/${slug}/version")"
  url="$(echo "${json}" | jq -r --arg v "${version}" --arg loader "${LOADER}" '
    first(
      .[]
      | select(.version_number == $v)
      | select(.loaders | index($loader))
      | .files[]
      | select(.primary)
      | .url
    ) // empty
  ')"
  filename="$(echo "${json}" | jq -r --arg v "${version}" --arg loader "${LOADER}" '
    first(
      .[]
      | select(.version_number == $v)
      | select(.loaders | index($loader))
      | .files[]
      | select(.primary)
      | .filename
    ) // empty
  ')"
  sha512="$(echo "${json}" | jq -r --arg v "${version}" --arg loader "${LOADER}" '
    first(
      .[]
      | select(.version_number == $v)
      | select(.loaders | index($loader))
      | .files[]
      | select(.primary)
      | .hashes.sha512
    ) // empty
  ')"

  if [[ -z "${url}" || -z "${filename}" || -z "${sha512}" ]]; then
    echo "No primary NeoForge Modrinth file for ${slug} version ${version}" >&2
    exit 1
  fi
  if [[ "${filename}" == *[Ff]abric* ]]; then
    echo "Refusing Fabric jar for ${slug}: ${filename}" >&2
    exit 1
  fi

  echo "Fetching ${slug} ${version} -> ${filename}"
  curl -fsSL -A "${USER_AGENT}" -o "${OUT_DIR}/${filename}" "${url}"
  echo "${sha512}  ${OUT_DIR}/${filename}" | sha512sum -c -
}

fetch_modrinth "kotlin-for-forge" "${KFF_VERSION}"
fetch_modrinth "borninchaos" "${CHAOS_VERSION}"
fetch_modrinth "born-in-configuration" "${BIC_VERSION}"
fetch_modrinth "geckolib" "${GECKOLIB_VERSION}"
