#!/bin/bash

# CONFIGURACIÓN GENERAL
BASE_URL="http://localhost:8081/api/v1"
ADMIN_EMAIL="admin@fastorder.com"
ADMIN_PASS="Admin123*"
CLIENTE_EMAIL="cliente@fastorder.com"
CLIENTE_PASS="Cliente123*"

echo "=== INICIANDO PRUEBAS UNITARIAS (DELIVERY) ==="

# 1. REGISTRO Y AUTENTICACIÓN
echo -e "\n[1] Registrando usuario CLIENTE..."
curl -s -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Cliente",
    "apellido": "Ejemplo",
    "correo": "'"$CLIENTE_EMAIL"'",
    "password": "'"$CLIENTE_PASS"'",
    "telefono": "55554321",
    "rol": "CLIENTE"
  }' | jq .

echo -e "\n[2] Autenticando usuario ADMIN..."
ADMIN_LOGIN_RESP=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "'"$ADMIN_EMAIL"'",
    "password": "'"$ADMIN_PASS"'"
  }')

ADMIN_TOKEN=$(echo $ADMIN_LOGIN_RESP | jq -r '.token // .accessToken')

if [ "$ADMIN_TOKEN" == "null" ] || [ -z "$ADMIN_TOKEN" ]; then
  echo "Error al obtener el token de ADMIN. Revisa credenciales o endpoint /auth/login."
  exit 1
fi

echo "Token Admin Obtenido correctamente."

# 2. PRUEBA DE CONTROL DE ACCESO O CONSULTA
echo -e "\n[3] Consultando categorías con Rol ADMIN..."
curl -s -X GET "$BASE_URL/categorias" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq .

echo -e "\n--> PRUEBAS COMPLETADAS"