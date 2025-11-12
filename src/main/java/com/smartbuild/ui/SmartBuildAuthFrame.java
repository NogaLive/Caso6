package com.smartbuild.ui;

import com.smartbuild.util.PasswordUtil;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SmartBuildAuthFrame extends JFrame {
    private final JTabbedPane tabs = new JTabbedPane();
    private final Map<String, String> users = new HashMap<>();

    public SmartBuildAuthFrame() {
        setTitle("SmartBuild - Login / Registro (Demo)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 360);
        setLocationRelativeTo(null);

        tabs.add("Ingresar", buildLoginPanel());
        tabs.add("Registrarse", buildRegisterPanel());

        getContentPane().add(tabs);
    }

    private JPanel buildLoginPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel userL = new JLabel("Usuario:");
        JTextField userT = new JTextField();
        JLabel passL = new JLabel("Contraseña:");
        JPasswordField passT = new JPasswordField();
        JButton login = new JButton("Ingresar");

        c.gridx=0; c.gridy=0; p.add(userL, c);
        c.gridx=1; c.gridy=0; p.add(userT, c);
        c.gridx=0; c.gridy=1; p.add(passL, c);
        c.gridx=1; c.gridy=1; p.add(passT, c);
        c.gridx=1; c.gridy=2; p.add(login, c);

        login.addActionListener(e -> {
            String u = userT.getText().trim();
            String pw = new String(passT.getPassword());
            if (u.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete usuario y contraseña");
                return;
            }
            String stored = users.get(u);
            if (stored == null) {
                JOptionPane.showMessageDialog(this, "Usuario no existe");
                return;
            }
            if (com.smartbuild.util.PasswordUtil.verify(pw, stored)) {
                JOptionPane.showMessageDialog(this, "Bienvenido, " + u + "!");
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales inválidas");
            }
        });

        return p;
    }

    private JPanel buildRegisterPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel userL = new JLabel("Usuario:");
        JTextField userT = new JTextField();
        JLabel passL = new JLabel("Contraseña:");
        JPasswordField passT = new JPasswordField();
        JLabel pass2L = new JLabel("Confirmar contraseña:");
        JPasswordField pass2T = new JPasswordField();
        JCheckBox terms = new JCheckBox("Acepto términos y condiciones");
        JButton register = new JButton("Crear cuenta");

        c.gridx=0; c.gridy=0; p.add(userL, c);
        c.gridx=1; c.gridy=0; p.add(userT, c);
        c.gridx=0; c.gridy=1; p.add(passL, c);
        c.gridx=1; c.gridy=1; p.add(passT, c);
        c.gridx=0; c.gridy=2; p.add(pass2L, c);
        c.gridx=1; c.gridy=2; p.add(pass2T, c);
        c.gridx=1; c.gridy=3; p.add(terms, c);
        c.gridx=1; c.gridy=4; p.add(register, c);

        register.addActionListener(e -> {
            String u = userT.getText().trim();
            String p1 = new String(passT.getPassword());
            String p2 = new String(pass2T.getPassword());

            if (u.isEmpty() || p1.isEmpty() || p2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }
            if (!p1.equals(p2)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
                return;
            }
            if (!terms.isSelected()) {
                JOptionPane.showMessageDialog(this, "Debe aceptar los términos");
                return;
            }
            if (users.containsKey(u)) {
                JOptionPane.showMessageDialog(this, "Usuario ya existe");
                return;
            }
            if (!com.smartbuild.util.PasswordUtil.isStrong(p1)) {
                JOptionPane.showMessageDialog(this, "Contraseña débil (mín 8, mayús, minús, dígito)");
                return;
            }
            String hash = com.smartbuild.util.PasswordUtil.hash(p1);
            users.put(u, hash);
            JOptionPane.showMessageDialog(this, "Usuario registrado: " + u);
        });

        return p;
    }
}
