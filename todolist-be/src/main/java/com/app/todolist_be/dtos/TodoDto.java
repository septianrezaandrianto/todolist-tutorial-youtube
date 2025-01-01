package com.app.todolist_be.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

    @NotBlank(message = "Waktu mulai tidak boleh kosong")
    @NotNull(message = "Waktu mulai tidak boleh kosong")
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])$", message = "Format waktu mulai tidak valid")
    private String startTime;
    @NotBlank(message = "Waktu selesai tidak boleh kosong")
    @NotNull(message = "Waktu selesai tidak boleh kosong")
    @Pattern(regexp = "^([01][0-9]|2[0-3]):([0-5][0-9])$", message = "Format waktu selesai tidak valid")
    private String endTime;
    @NotBlank(message = "Aktifitas tidak boleh kosong")
    @NotNull(message = "Aktifitas tidak boleh kosong")
    @Size(max=100, message = "Panjang aktifitas maksimal 100 karakter")
    private String title;
    @NotBlank(message = "Prioritas tidak boleh kosong")
    @NotNull(message = "Prioritas tidak boleh kosong")
    @Size(max=10, message = "Panjang aktifitas maksimal 10 karakter")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Format prioritas tidak valid")
    private String priority;
    @NotBlank(message = "Nomor WhatsApp tidak boleh kosong")
    @NotNull(message = "Nomor WhatsApp tidak boleh kosong")
    @Pattern(regexp = "^\\+62\\d+$", message = "Format nomor whatsapp tidak valid")
    @Size(max = 15, message = "Panjang nomor whatsapp maksimal 15 karakter")
    private String waNumber;

}
