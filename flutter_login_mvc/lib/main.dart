import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'controllers/login_controller.dart';
import 'views/login_view.dart';
import 'services/api_service.dart';
import 'repositories/login_repository.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  
  // Inicializa dependências
  await _setupDependencies();
  
  runApp(const MyApp());
}

Future<void> _setupDependencies() async {
  final prefs = await SharedPreferences.getInstance();
  final dio = Dio(
    BaseOptions(
      connectTimeout: Duration(seconds: 15),
      receiveTimeout: Duration(seconds: 15),
      sendTimeout: Duration(seconds: 15),
    ),
  );

  // Registra dependências com GetX
  Get.put<SharedPreferences>(prefs);
  Get.put<Dio>(dio);
  Get.put<ApiService>(ApiService(dio, baseUrl: 'http://seu_servidor'));
  Get.put<LoginRepository>(
    LoginRepository(
      apiService: Get.find<ApiService>(),
      prefs: prefs,
    ),
  );
  Get.put<LoginController>(
    LoginController(repository: Get.find<LoginRepository>()),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      title: 'RCK Sistemas',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primaryColor: Color(0xFF1E3C72),
        useMaterial3: true,
        fontFamily: 'Roboto',
      ),
      initialRoute: '/login',
      getPages: [
        GetPage(
          name: '/login',
          page: () => const LoginView(),
          transition: Transition.fade,
        ),
        // Adicione outras rotas aqui
      ],
    );
  }
}

