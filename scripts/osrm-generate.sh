#!/bin/bash
set -e

mkdir -p osrm-data
cd osrm-data

if [ ! -f argentina-latest.osm.pbf ]; then
  echo "Descargando mapa de Argentina..."
  wget https://download.geofabrik.de/south-america/argentina-latest.osm.pbf
fi

docker run -t -v $(pwd):/data osrm/osrm-backend \
  osrm-extract -p /opt/car.lua /data/argentina-latest.osm.pbf

docker run -t -v $(pwd):/data osrm/osrm-backend \
  osrm-contract /data/argentina-latest.osrm

echo "OSRM listo"
