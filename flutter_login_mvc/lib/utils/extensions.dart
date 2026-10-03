/// Extensões úteis para o projeto Flutter Login MVC

extension StringExtension on String {
  /// Verifica se é um email válido
  bool get isValidEmail {
    final emailRegex = RegExp(
      r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'
    );
    return emailRegex.hasMatch(this);
  }

  /// Remove espaços em branco
  String get trimmed => trim();

  /// Converte para uppercase
  String get upper => toUpperCase();

  /// Converte para lowercase
  String get lower => toLowerCase();

  /// Verifica se tem mínimo de caracteres
  bool hasMinLength(int min) => length >= min;

  /// Máscara de senha (mostra apenas *)
  String get masked => '*' * length;

  /// Capitaliza primeira letra
  String get capitalized =>
      isEmpty ? '' : '${this[0].toUpperCase()}${substring(1)}';
}

extension IntExtension on int {
  /// Verifica se status é sucesso
  bool get isSuccessStatus => this == 200;

  /// Verifica se status é erro de validação
  bool get isBadRequest => this == 400;

  /// Verifica se status é não encontrado
  bool get isNotFound => this == 404;

  /// Verifica se status é erro do servidor
  bool get isServerError => this == 500;

  /// Verifica se é timeout
  bool get isTimeout => this == 408;
}

extension BoolExtension on bool {
  /// Toggle boolean
  bool get toggle => !this;

  /// Retorna string 'S' ou 'N' (padrão RCK)
  String get toRckFormat => this ? 'S' : 'N';
}

extension ListExtension<T> on List<T> {
  /// Retorna primeiro elemento ou null
  T? get firstOrNull => isNotEmpty ? first : null;

  /// Retorna último elemento ou null
  T? get lastOrNull => isNotEmpty ? last : null;

  /// Verifica se tem itens
  bool get isNotEmpty => length > 0;
}

extension DateTimeExtension on DateTime {
  /// Formato: "dd/MM/yyyy HH:mm:ss"
  String get formatted {
    return "$day/$month/$year ${hour.toString().padLeft(2, '0')}:${minute.toString().padLeft(2, '0')}:${second.toString().padLeft(2, '0')}";
  }

  /// Apenas data: "dd/MM/yyyy"
  String get dateOnly {
    return "$day/$month/$year";
  }

  /// Apenas hora: "HH:mm"
  String get timeOnly {
    return "${hour.toString().padLeft(2, '0')}:${minute.toString().padLeft(2, '0')}";
  }
}

extension DurationExtension on Duration {
  /// Formato: "1h 30m"
  String get formatted {
    final hours = inHours;
    final minutes = inMinutes.remainder(60);
    return '${hours}h ${minutes}m';
  }
}

extension NumExtension on num {
  /// Formata moeda brasileira
  String get toCurrency {
    return 'R\$ ${toStringAsFixed(2).replaceAll('.', ',')}';
  }

  /// Formata com mil separador
  String get formatNumber {
    return toString().replaceAllMapped(
      RegExp(r'\B(?=(\d{3})+(?!\d))'),
      (match) => '.',
    );
  }
}

