import 'package:json_annotation/json_annotation.dart';
import 'message.dart';

part 'conversation.g.dart';

@JsonSerializable()
class Conversation {
  final String id;
  final String title;
  @JsonKey(fromJson: _dateTimeFromJson, toJson: _dateTimeToJson)
  final DateTime createdAt;
  final List<Message> messages;
  final int messageCount;
  final String? lastMessage;
  @JsonKey(fromJson: _dateTimeFromJsonNullable, toJson: _dateTimeToJsonNullable)
  final DateTime? lastMessageAt;

  const Conversation({
    required this.id,
    required this.title,
    required this.createdAt,
    required this.messages,
    required this.messageCount,
    this.lastMessage,
    this.lastMessageAt,
  });

  factory Conversation.fromJson(Map<String, dynamic> json) => _$ConversationFromJson(json);
  Map<String, dynamic> toJson() => _$ConversationToJson(this);

  Conversation copyWith({
    String? id,
    String? title,
    DateTime? createdAt,
    List<Message>? messages,
    int? messageCount,
    String? lastMessage,
    DateTime? lastMessageAt,
  }) {
    return Conversation(
      id: id ?? this.id,
      title: title ?? this.title,
      createdAt: createdAt ?? this.createdAt,
      messages: messages ?? this.messages,
      messageCount: messageCount ?? this.messageCount,
      lastMessage: lastMessage ?? this.lastMessage,
      lastMessageAt: lastMessageAt ?? this.lastMessageAt,
    );
  }

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

  static DateTime? _dateTimeFromJsonNullable(dynamic json) {
    if (json == null) return null;
    return _dateTimeFromJson(json);
  }

  static dynamic _dateTimeToJson(DateTime dateTime) {
    return dateTime.toIso8601String();
  }

  static dynamic _dateTimeToJsonNullable(DateTime? dateTime) {
    return dateTime?.toIso8601String();
  }
}

@JsonSerializable()
class ConversationSummary {
  final String id;
  final String title;
  @JsonKey(fromJson: _dateTimeFromJson, toJson: _dateTimeToJson)
  final DateTime createdAt;
  final int messageCount;
  final String? lastMessage;
  @JsonKey(fromJson: _dateTimeFromJsonNullable, toJson: _dateTimeToJsonNullable)
  final DateTime? lastMessageAt;

  const ConversationSummary({
    required this.id,
    required this.title,
    required this.createdAt,
    required this.messageCount,
    this.lastMessage,
    this.lastMessageAt,
  });

  factory ConversationSummary.fromJson(Map<String, dynamic> json) => _$ConversationSummaryFromJson(json);
  Map<String, dynamic> toJson() => _$ConversationSummaryToJson(this);

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

  static DateTime? _dateTimeFromJsonNullable(dynamic json) {
    if (json == null) return null;
    return _dateTimeFromJson(json);
  }

  static dynamic _dateTimeToJson(DateTime dateTime) {
    return dateTime.toIso8601String();
  }

  static dynamic _dateTimeToJsonNullable(DateTime? dateTime) {
    return dateTime?.toIso8601String();
  }
}
