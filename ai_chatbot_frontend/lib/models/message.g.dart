// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'message.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Message _$MessageFromJson(Map<String, dynamic> json) => Message(
  id: json['id'] as String,
  role: json['role'] as String,
  content: json['content'] as String,
  createdAt: Message._dateTimeFromJson(json['createdAt']),
);

Map<String, dynamic> _$MessageToJson(Message instance) => <String, dynamic>{
  'id': instance.id,
  'role': instance.role,
  'content': instance.content,
  'createdAt': Message._dateTimeToJson(instance.createdAt),
};

MessageRequest _$MessageRequestFromJson(Map<String, dynamic> json) =>
    MessageRequest(
      conversationId: json['conversationId'] as String?,
      message: json['message'] as String,
      model: json['model'] as String?,
    );

Map<String, dynamic> _$MessageRequestToJson(MessageRequest instance) =>
    <String, dynamic>{
      'conversationId': instance.conversationId,
      'message': instance.message,
      'model': instance.model,
    };

MessageResponse _$MessageResponseFromJson(Map<String, dynamic> json) =>
    MessageResponse(
      id: json['id'] as String,
      object: json['object'] as String,
      created: (json['created'] as num).toInt(),
      model: json['model'] as String,
      provider: json['provider'] as String?,
      conversationId: json['conversationId'] as String?,
      choices: (json['choices'] as List<dynamic>)
          .map((e) => Choice.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$MessageResponseToJson(MessageResponse instance) =>
    <String, dynamic>{
      'id': instance.id,
      'object': instance.object,
      'created': instance.created,
      'model': instance.model,
      'provider': instance.provider,
      'conversationId': instance.conversationId,
      'choices': instance.choices,
    };

Choice _$ChoiceFromJson(Map<String, dynamic> json) => Choice(
  index: (json['index'] as num).toInt(),
  message: ResponseMessage.fromJson(json['message'] as Map<String, dynamic>),
  finishReason: json['finish_reason'] as String,
);

Map<String, dynamic> _$ChoiceToJson(Choice instance) => <String, dynamic>{
  'index': instance.index,
  'message': instance.message,
  'finish_reason': instance.finishReason,
};

ResponseMessage _$ResponseMessageFromJson(Map<String, dynamic> json) =>
    ResponseMessage(
      role: json['role'] as String,
      content: json['content'] as String,
    );

Map<String, dynamic> _$ResponseMessageToJson(ResponseMessage instance) =>
    <String, dynamic>{'role': instance.role, 'content': instance.content};
