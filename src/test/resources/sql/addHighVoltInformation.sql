
insert into high_volt_information (high_volt_information_id, high_voltage_air_line_inductive_resistance,
                                   high_voltage_cable_line_inductive_resistance,
                                   high_voltage_cable_line_active_resistance, surge_coefficient,
                                   economic_current_density, short_circuit_time,
                                   coefficient_taking_emitted_heat_difference, production_hall_transformer_full_power,
                                   base_voltage, base_full_power, relative_baseline_unrestricted_power_resistance,
                                   high_voltage_air_line_length, head_transformer_full_power, short_circuit_voltage,
                                   cable_line_length, rated_voltage_of_higher_voltage_winding_of_transformer,
                                   relative_basis_resistance, power_line_relative_resistance,
                                   first_transformer_relative_reactive_resistance,
                                   cable_line_relative_reactive_resistance,
                                   second_transformer_relative_reactive_resistance, reactive_resistance_at_point_k_1,
                                   base_current_at_point_k_1, full_resistance_at_point_k_1,
                                   short_circuit_current_at_point_k_1, surge_current_at_point_k_1,
                                   short_circuit_power_at_point_k_1, rated_power_transformer_current)
values (3, 0.08, 0.08, 0.18, 1.8, 1.2, 0.5, 85.0, 400, 115, 300, 0.5, 40, 160, 10.5, 2.5,
        10, 0.5, 0.07, 0.2, 0.54, 41.25, 1.31, 16.5, 1.88, 8.78, 22.35, 15.96, 23.09),

       (6, 0.08, 0.08, 0.18, 1.8, 1.2, 0.5, 85.0, 400, 115, 300, 0.5, 60, 180, 10.5, 5.0,
        6.0, 0.7, 0.11, 0.18, 1.09, 41.25, 1.9, 16.5, 7.74, 2.13, 5.42, 3.88, 38.49);