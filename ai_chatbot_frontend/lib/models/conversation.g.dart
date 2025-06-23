// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'conversation.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Conversation _$ConversationFromJson(Map<String, dynamic> json) => Conversation(
  id: json['id'] as String,
  title: json['title'] as String,
  createdAt: Conversation._dateTimeFromJson(json['createdAt']),
  messages: (json['messages'] as List<dynamic>)
      .map((e) => Message.fromJson(e as Map<String, dynamic>))
      .toList(),
  messageCount: (json['messageCount'] as num).toInt(),
  lastMessage: json['lastMessage'] as String?,
  lastMessageAt: Conversation._dateTimeFromJsonNullable(json['lastMessageAt']),
);

Map<String, dynamic> _$ConversationToJson(
  Conversation instance,
) => <String, dynamic>{
  'id': instance.id,
  'title': instance.title,
  'createdAt': Conversation._dateTimeToJson(instance.createdAt),
  'messages': instance.messages,
  'messageCount': instance.messageCount,
  'lastMessage': instance.lastMessage,
  'lastMessageAt': Conversation._dateTimeToJsonNullable(instance.lastMessageAt),
};

ConversationSummary _$ConversationSummaryFromJson(Map<String, dynamic> json) =>
    ConversationSummary(
      id: json['id'] as String,
      title: json['title'] as String,
      createdAt: ConversationSummary._dateTimeFromJson(json['createdAt']),
      messageCount: (json['messageCount'] as num).toInt(),
      lastMessage: json['lastMessage'] as String?,
      lastMessageAt: ConversationSummary._dateTimeFromJsonNullable(
        json['lastMessageAt'],
      ),
    );

Map<String, dynamic> _$ConversationSummaryToJson(
  ConversationSummary instance,
) => <String, dynamic>{
  'id': instance.id,
  'title': instance.title,
  'createdAt': ConversationSummary._dateTimeToJson(instance.createdAt),
  'messageCount': instance.messageCount,
  'lastMessage': instance.lastMessage,
  'lastMessageAt': ConversationSummary._dateTimeToJsonNullable(
    instance.lastMessageAt,
  ),
};
