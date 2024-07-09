create table high_volt_cables
(
    high_volt_cables_id int2 primary key,
    cable_type varchar(255) not null
);

create table high_volt_cable_selection
(
    high_volt_cable_selection_id int2 primary key,
    min_cable_section_for_select float4 not null
);

create table inductive_impedance_areas
(
    inductive_impedance_areas_id  int2 primary key,
    inductive_resistance float4 not null
);

create table high_volt_information
(
    high_volt_information_id  int2 primary key,
    high_voltage_air_line_inductive_resistance float4 not null,
    high_voltage_cable_line_inductive_resistance float4 not null,
    high_voltage_cable_line_active_resistance float4 not null,
    surge_coefficient float4 not null,
    economic_current_density float4 not null,
    short_circuit_time float4 not null,
    coefficient_taking_emitted_heat_difference float4 not null,
    production_hall_transformer_full_power float4 not null,
    base_voltage int2 not null,
    base_full_power int2   not null,
    relative_baseline_unrestricted_power_resistance float4 not null,
    high_voltage_air_line_length float4 not null,
    head_transformer_full_power float4 not null,
    short_circuit_voltage float4 not null,
    cable_line_length float4 not null,
    rated_voltage_of_higher_voltage_winding_of_transformer float4 not null,
    relative_basis_resistance float4 not null,
    power_line_relative_resistance float4 not null,
    first_transformer_relative_reactive_resistance float4 not null,
    cable_line_relative_reactive_resistance float4 not null,
    second_transformer_relative_reactive_resistance float4 not null,
    reactive_resistance_at_point_k_1 float4 not null,
    base_current_at_point_k_1 float4 not null,
    full_resistance_at_point_k_1 float4 not null,
    short_circuit_current_at_point_k_1 float4 not null,
    surge_current_at_point_k_1 float4 not null,
    short_circuit_power_at_point_k_1 float4 not null,
    rated_power_transformer_current float4 not null
);

create table high_volt_information_inductive_impedance_areas
(
    high_volt_information_inductive_impedance_areas_id  int2 primary key,
    inductive_impedance_areas_id_fk int2,
    high_volt_information_id_fk int2,
    FOREIGN KEY (inductive_impedance_areas_id_fk) REFERENCES inductive_impedance_areas(inductive_impedance_areas_id),
    FOREIGN KEY (high_volt_information_id_fk) REFERENCES high_volt_information(high_volt_information_id)
);