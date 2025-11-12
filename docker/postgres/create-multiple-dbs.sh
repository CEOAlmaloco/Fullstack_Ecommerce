#!/bin/bash
set -euo pipefail

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE levelup_auth;
    CREATE DATABASE levelup_usuario;
    CREATE DATABASE levelup_productos;
    CREATE DATABASE levelup_carrito;
    CREATE DATABASE levelup_pedido;
    CREATE DATABASE levelup_pagos;
    CREATE DATABASE levelup_resenia;
    CREATE DATABASE levelup_referidos;
    CREATE DATABASE levelup_promociones;
    CREATE DATABASE levelup_inventario;
    CREATE DATABASE levelup_notificaciones;
    CREATE DATABASE levelup_eventos;
    CREATE DATABASE levelup_contenido;
EOSQL

