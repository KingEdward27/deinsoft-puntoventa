Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

# Configuración
$jarPath = "punto-venta-client.jar"
$logPath = "punto-venta-client.log"
$pidFile = "miApp.pid"

# Crear formulario
$form = New-Object Windows.Forms.Form
$form.Text = "Gestor Java App"
$form.Size = New-Object Drawing.Size(400,200)
$form.StartPosition = "CenterScreen"

# Botón Iniciar
$btnStart = New-Object Windows.Forms.Button
$btnStart.Text = "Iniciar"
$btnStart.Size = New-Object Drawing.Size(80,30)
$btnStart.Location = New-Object Drawing.Point(30,30)
$form.Controls.Add($btnStart)

# Botón Detener
$btnStop = New-Object Windows.Forms.Button
$btnStop.Text = "Detener"
$btnStop.Size = New-Object Drawing.Size(80,30)
$btnStop.Location = New-Object Drawing.Point(120,30)
$form.Controls.Add($btnStop)

# Botón Estado
$btnStatus = New-Object Windows.Forms.Button
$btnStatus.Text = "Estado"
$btnStatus.Size = New-Object Drawing.Size(80,30)
$btnStatus.Location = New-Object Drawing.Point(210,30)
$form.Controls.Add($btnStatus)

# Etiqueta para mostrar estado
$lblStatus = New-Object Windows.Forms.Label
$lblStatus.Text = "Estado: desconocido"
$lblStatus.AutoSize = $true
$lblStatus.Location = New-Object Drawing.Point(30,80)
$form.Controls.Add($lblStatus)

# Función para iniciar
$btnStart.Add_Click({
    if (Test-Path $pidFile) {
        [System.Windows.Forms.MessageBox]::Show("Ya está corriendo.")
        return
    }

    $psi = New-Object System.Diagnostics.ProcessStartInfo
    $psi.FileName = "java"
    $psi.Arguments = "-jar `"$jarPath`""
    $psi.RedirectStandardOutput = $true
    $psi.RedirectStandardError = $true
    $psi.UseShellExecute = $false
    $psi.CreateNoWindow = $true

    $process = New-Object System.Diagnostics.Process
    $process.StartInfo = $psi

    $logStream = [System.IO.StreamWriter]::new($logPath, $true)
    $process.Start() | Out-Null

    # Redirigir salida
    $process.BeginOutputReadLine()
    $process.BeginErrorReadLine()
    $process.OutputDataReceived += { $logStream.WriteLine($_.Data) }
    $process.ErrorDataReceived += { $logStream.WriteLine($_.Data) }

    $process.Id | Out-File $pidFile
    $lblStatus.Text = "Estado: iniciado (PID $($process.Id))"
})

# Función para detener
$btnStop.Add_Click({
    if (-not (Test-Path $pidFile)) {
        [System.Windows.Forms.MessageBox]::Show("No está corriendo.")
        return
    }

    $pid = Get-Content $pidFile
    try {
        Stop-Process -Id $pid -Force
        Remove-Item $pidFile -Force
        $lblStatus.Text = "Estado: detenido"
    } catch {
        [System.Windows.Forms.MessageBox]::Show("Error al detener proceso.")
    }
})

# Función para mostrar estado
$btnStatus.Add_Click({
    if (-not (Test-Path $pidFile)) {
        $lblStatus.Text = "Estado: no está corriendo"
        return
    }

    $pid = Get-Content $pidFile
    $proc = Get-Process -Id $pid -ErrorAction SilentlyContinue
    if ($proc) {
        $lblStatus.Text = "Estado: corriendo (PID $pid)"
    } else {
        $lblStatus.Text = "Estado: PID guardado pero sin proceso"
    }
})

# Mostrar el formulario
[void]$form.ShowDialog()