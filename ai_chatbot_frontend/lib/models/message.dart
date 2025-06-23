import 'package:json_annotation/json_annotation.dart';

part 'message.g.dart';

@JsonSerializable()
class Message {
  final String id;
  final String role;
  final String content;
  @JsonKey(fromJson: _dateTimeFromJson, toJson: _dateTimeToJson)
  final DateTime createdAt;

  const Message({
    required this.id,
    required this.role,
    required this.content,
    required this.createdAt,
  });

  factory Message.fromJson(Map<String, dynamic> json) => _$MessageFromJson(json);
  Map<String, dynamic> toJson() => _$MessageToJson(this);

  bool get isUser => role == 'user';
  bool get isAssistant => role == 'assistant';

  static DateTime _dateTimeFromJson(dynamic json) {
    if (json is String) {
      return DateTime.parse(json);
    } else if (json is List && json.length >= 6) {
      return DateTime(
        json[0] as int, // year
        json[1] as int, // month
        json[2] as int, // day
        json[3] as int, // hour
        json[4] as int, // minute
        json[5] as int, // second
        json.length > 6 ? (json[6] as int) ~/ 1000000 : 0, // milliseconds
      );
    }
    return DateTime.now();
  }

  static dynamic _dateTimeToJson(DateTime dateTime) {
    return dateTime.toIso8601String();
  }
}

@JsonSerializable()
class MessageRequest {
  final String? conversationId;
  final String message;
  final String? model;

  const MessageRequest({
    this.conversationId,
    required this.message,
    this.model,
  });

  factory MessageRequest.fromJson(Map<String, dynamic> json) => _$MessageRequestFromJson(json);
  Map<String, dynamic> toJson() => _$MessageRequestToJson(this);
}

@JsonSerializable()
class MessageResponse {
  final String id;
  final String object;
  final int created;
  final String model;
  final String? provider;
  final String? conversationId;
  final List<Choice> choices;

  const MessageResponse({
    required this.id,
    required this.object,
    required this.created,
    required this.model,
    this.provider,
    this.conversationId,
    required this.choices,
  });

  factory MessageResponse.fromJson(Map<String, dynamic> json) => _$MessageResponseFromJson(json);
  Map<String, dynamic> toJson() => _$MessageResponseToJson(this);

  String? get content {
    if (choices.isNotEmpty) {
      return choices.first.message.content;
    }
    return null;
  }
}

@JsonSerializable()
class Choice {
  final int index;
  final ResponseMessage message;
  @JsonKey(name: 'finish_reason')
  final String finishReason;

  const Choice({
    required this.index,
    required this.message,
    required this.finishReason,
  });

  factory Choice.fromJson(Map<String, dynamic> json) => _$ChoiceFromJson(json);
  Map<String, dynamic> toJson() => _$ChoiceToJson(this);
}

@JsonSerializable()
class ResponseMessage {
  final String role;
  final String content;

  const ResponseMessage({
    required this.role,
    required this.content,
  });

  factory ResponseMessage.fromJson(Map<String, dynamic> json) => _$ResponseMessageFromJson(json);
  Map<String, dynamic> toJson() => _$ResponseMessageToJson(this);
}
