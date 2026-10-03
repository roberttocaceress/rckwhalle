import 'package:flutter/material.dart';
import 'package:get/get.dart';
import '../controllers/login_controller.dart';

class LoginView extends GetView<LoginController> {
  const LoginView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SingleChildScrollView(
        child: Container(
          height: context.height,
          decoration: _buildBackgroundGradient(),
          child: Column(
            children: [
              // Header com logo
              _buildHeader(),
              // Form
              Expanded(
                child: Align(
                  alignment: Alignment.bottomCenter,
                  child: _buildLoginForm(context),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  // ============= Build Methods =============

  BoxDecoration _buildBackgroundGradient() {
    return BoxDecoration(
      gradient: LinearGradient(
        begin: Alignment.topLeft,
        end: Alignment.bottomRight,
        colors: [
          Color(0xFF1E3C72).withOpacity(0.9),
          Color(0xFF2A5298).withOpacity(0.9),
        ],
      ),
    );
  }

  Widget _buildHeader() {
    return Container(
      padding: EdgeInsets.symmetric(vertical: 40, horizontal: 20),
      child: Column(
        children: [
          // Logo da RCK
          Container(
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(20),
              boxShadow: [
                BoxShadow(
                  color: Colors.black.withOpacity(0.2),
                  blurRadius: 10,
                  offset: Offset(0, 5),
                ),
              ],
            ),
            padding: EdgeInsets.all(20),
            child: Icon(
              Icons.shopping_bag_outlined,
              size: 60,
              color: Color(0xFF1E3C72),
            ),
          ),
          SizedBox(height: 20),
          Text(
            'RCK SISTEMAS',
            style: TextStyle(
              color: Colors.white,
              fontSize: 24,
              fontWeight: FontWeight.bold,
            ),
          ),
          SizedBox(height: 8),
          Text(
            'Sistema de Gestão de Vendas',
            style: TextStyle(
              color: Colors.white.withOpacity(0.8),
              fontSize: 14,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildLoginForm(BuildContext context) {
    return Container(
      width: double.infinity,
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.vertical(top: Radius.circular(30)),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 20,
            offset: Offset(0, -5),
          ),
        ],
      ),
      child: Padding(
        padding: EdgeInsets.symmetric(horizontal: 24, vertical: 32),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(
              'Bem-vindo de volta!',
              style: TextStyle(
                fontSize: 22,
                fontWeight: FontWeight.bold,
                color: Color(0xFF1E3C72),
              ),
            ),
            SizedBox(height: 8),
            Text(
              'Faça login para continuar',
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey[600],
              ),
            ),
            SizedBox(height: 32),
            // Campo Login
            _buildLoginTextField(),
            SizedBox(height: 16),
            // Campo Senha
            _buildPasswordTextField(),
            SizedBox(height: 24),
            // Botão Login
            _buildLoginButton(),
            SizedBox(height: 16),
            // Botão Biometria
            _buildBiometricButton(),
            SizedBox(height: 24),
            // Links principais
            _buildFooterLinks(),
            SizedBox(height: 16),
          ],
        ),
      ),
    );
  }

  Widget _buildLoginTextField() {
    return Obx(() {
      return TextField(
        controller: controller.loginController,
        enabled: !controller.isLoading.value,
        decoration: InputDecoration(
          hintText: 'Usuário ou Email',
          hintStyle: TextStyle(color: Colors.grey[400]),
          errorText: controller.loginError.isEmpty ? null : controller.loginError.value,
          prefixIcon: Icon(Icons.person_outline, color: Color(0xFF1E3C72)),
          suffixIcon: controller.loginController.text.isNotEmpty
              ? Icon(Icons.check_circle, color: Colors.green, size: 20)
              : null,
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: BorderSide(color: Colors.grey[300]!, width: 1),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: BorderSide(color: Color(0xFF1E3C72), width: 2),
          ),
          errorBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: BorderSide(color: Colors.red, width: 1),
          ),
          contentPadding: EdgeInsets.symmetric(horizontal: 16, vertical: 14),
        ),
        onChanged: (_) => controller.update(),
      );
    });
  }

  Widget _buildPasswordTextField() {
    return Obx(() {
      return TextField(
        controller: controller.senhaController,
        enabled: !controller.isLoading.value,
        obscureText: !controller.showPassword.value,
        decoration: InputDecoration(
          hintText: 'Senha',
          hintStyle: TextStyle(color: Colors.grey[400]),
          errorText: controller.senhaError.isEmpty ? null : controller.senhaError.value,
          prefixIcon: Icon(Icons.lock_outline, color: Color(0xFF1E3C72)),
          suffixIcon: GestureDetector(
            onTap: controller.togglePasswordVisibility,
            child: Icon(
              controller.showPassword.value ? Icons.visibility : Icons.visibility_off,
              color: Color(0xFF1E3C72),
            ),
          ),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: BorderSide(color: Colors.grey[300]!, width: 1),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: BorderSide(color: Color(0xFF1E3C72), width: 2),
          ),
          errorBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: BorderSide(color: Colors.red, width: 1),
          ),
          contentPadding: EdgeInsets.symmetric(horizontal: 16, vertical: 14),
        ),
        onChanged: (_) => controller.update(),
      );
    });
  }

  Widget _buildLoginButton() {
    return Obx(() {
      return AnimatedOpacity(
        opacity: controller.showLoginButton.value ? 1.0 : 0.5,
        duration: Duration(milliseconds: 300),
        child: Container(
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
              colors: [
                Color(0xFF1E3C72),
                Color(0xFF2A5298),
              ],
            ),
            borderRadius: BorderRadius.circular(12),
            boxShadow: [
              BoxShadow(
                color: Color(0xFF1E3C72).withOpacity(0.3),
                blurRadius: 8,
                offset: Offset(0, 4),
              ),
            ],
          ),
          child: Material(
            color: Colors.transparent,
            child: InkWell(
              onTap: controller.showLoginButton.value && !controller.isLoading.value
                  ? controller.login
                  : null,
              borderRadius: BorderRadius.circular(12),
              child: Padding(
                padding: EdgeInsets.symmetric(vertical: 16),
                child: controller.isLoading.value
                    ? SizedBox(
                        height: 20,
                        width: 20,
                        child: CircularProgressIndicator(
                          valueColor: AlwaysStoppedAnimation(Colors.white),
                          strokeWidth: 2,
                        ),
                      )
                    : Text(
                        'ENTRAR',
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          color: Colors.white,
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                          letterSpacing: 1.2,
                        ),
                      ),
              ),
            ),
          ),
        ),
      );
    });
  }

  Widget _buildBiometricButton() {
    return Obx(() {
      if (!controller.isBiometricsAvailable.value || !controller.isBiometricsEnabled.value) {
        return SizedBox.shrink();
      }

      return Container(
        decoration: BoxDecoration(
          border: Border.all(color: Color(0xFF1E3C72), width: 2),
          borderRadius: BorderRadius.circular(12),
        ),
        child: Material(
          color: Colors.transparent,
          child: InkWell(
            onTap: !controller.isLoading.value ? controller.loginWithBiometrics : null,
            borderRadius: BorderRadius.circular(12),
            child: Padding(
              padding: EdgeInsets.symmetric(vertical: 14),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(Icons.fingerprint, color: Color(0xFF1E3C72), size: 24),
                  SizedBox(width: 12),
                  Text(
                    'Login Biométrico',
                    style: TextStyle(
                      color: Color(0xFF1E3C72),
                      fontSize: 14,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      );
    });
  }

  Widget _buildFooterLinks() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        _buildFooterLink(
          icon: Icons.lock_reset_outlined,
          label: 'Recuperar\nSenha',
          onTap: () => Get.toNamed('/recuperar-senha'),
        ),
        _buildFooterLink(
          icon: Icons.help_outline,
          label: 'Ajuda',
          onTap: () => Get.toNamed('/ajuda'),
        ),
        _buildFooterLink(
          icon: Icons.info_outline,
          label: 'Sobre',
          onTap: () => Get.toNamed('/sobre'),
        ),
      ],
    );
  }

  Widget _buildFooterLink({
    required IconData icon,
    required String label,
    required VoidCallback onTap,
  }) {
    return InkWell(
      onTap: onTap,
      child: Column(
        children: [
          Container(
            padding: EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: Colors.grey[100],
              borderRadius: BorderRadius.circular(12),
            ),
            child: Icon(icon, color: Color(0xFF1E3C72), size: 24),
          ),
          SizedBox(height: 8),
          Text(
            label,
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 11,
              color: Colors.grey[700],
              fontWeight: FontWeight.w500,
            ),
          ),
        ],
      ),
    );
  }
}

