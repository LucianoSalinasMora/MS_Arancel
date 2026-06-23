INSERT IGNORE INTO arancel (cuenta_id, estudiante_id, arancel_base_anual, costo_acumulado_total, saldo_pendiente, semestres_extra, factor_reajuste, estado_cuenta)
VALUES (1, '20.123.456-7', 3500000.0, 0.0, 1500000.0, 0, 0.05, 'AL_DIA')
ON DUPLICATE KEY UPDATE cuenta_id=cuenta_id;