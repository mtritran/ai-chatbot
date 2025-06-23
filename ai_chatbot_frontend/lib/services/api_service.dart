import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/message.dart';
import '../models/conversation.dart';

class ApiService {
  static const String baseUrl = 'http://localhost:8080/api';
  
  final http.Client _client = http.Client();

  Map<String, String> get _headers => {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  };

  Future<MessageResponse> sendMessage(MessageRequest request) async {
    try {
      print('Sending message to: $baseUrl/chat');
      print('Request body: ${jsonEncode(request.toJson())}');
      
      final response = await _client.post(
        Uri.parse('$baseUrl/chat'),
        headers: _headers,
        body: jsonEncode(request.toJson()),
      );

      print('Response status: ${response.statusCode}');
      print('Response body: ${response.body}');

      if (response.statusCode == 200) {
        final jsonData = jsonDecode(response.body);
        return MessageResponse.fromJson(jsonData);
      } else {
        throw ApiException(
          'Failed to send message: ${response.statusCode}',
          response.statusCode,
        );
      }
    } catch (e) {
      print('Error in sendMessage: $e');
      if (e is ApiException) rethrow;
      throw ApiException('Network error: $e', 0);
    }
  }

  Future<List<ConversationSummary>> getConversations() async {
    try {
      print('Getting conversations from: $baseUrl/conversations');
      
      final response = await _client.get(
        Uri.parse('$baseUrl/conversations'),
        headers: _headers,
      );

      print('Response status: ${response.statusCode}');
      print('Response body length: ${response.body.length}');

      if (response.statusCode == 200) {
        final List<dynamic> jsonData = jsonDecode(response.body);
        print('Parsed ${jsonData.length} conversations');
        
        final conversations = jsonData.map((json) {
          try {
            return ConversationSummary.fromJson(json);
          } catch (e) {
            print('Error parsing conversation: $e');
            print('JSON: $json');
            rethrow;
          }
        }).toList();
        
        print('Successfully parsed all conversations');
        return conversations;
      } else {
        throw ApiException(
          'Failed to get conversations: ${response.statusCode}',
          response.statusCode,
        );
      }
    } catch (e) {
      print('Error in getConversations: $e');
      if (e is ApiException) rethrow;
      throw ApiException('Network error: $e', 0);
    }
  }

  Future<Conversation> getConversation(String id) async {
    try {
      print('Getting conversation: $id');
      
      final response = await _client.get(
        Uri.parse('$baseUrl/conversations/$id'),
        headers: _headers,
      );

      print('Response status: ${response.statusCode}');

      if (response.statusCode == 200) {
        final jsonData = jsonDecode(response.body);
        return Conversation.fromJson(jsonData);
      } else {
        throw ApiException(
          'Failed to get conversation: ${response.statusCode}',
          response.statusCode,
        );
      }
    } catch (e) {
      print('Error in getConversation: $e');
      if (e is ApiException) rethrow;
      throw ApiException('Network error: $e', 0);
    }
  }

  Future<ConversationSummary> createConversation() async {
    try {
      print('Creating new conversation');
      
      final response = await _client.post(
        Uri.parse('$baseUrl/conversations'),
        headers: _headers,
      );

      print('Response status: ${response.statusCode}');

      if (response.statusCode == 200) {
        final jsonData = jsonDecode(response.body);
        return ConversationSummary.fromJson(jsonData);
      } else {
        throw ApiException(
          'Failed to create conversation: ${response.statusCode}',
          response.statusCode,
        );
      }
    } catch (e) {
      print('Error in createConversation: $e');
      if (e is ApiException) rethrow;
      throw ApiException('Network error: $e', 0);
    }
  }

  Future<void> deleteConversation(String id) async {
    try {
      print('Deleting conversation: $id');
      
      final response = await _client.delete(
        Uri.parse('$baseUrl/conversations/$id'),
        headers: _headers,
      );

      print('Delete response status: ${response.statusCode}');

      if (response.statusCode != 200) {
        throw ApiException(
          'Failed to delete conversation: ${response.statusCode}',
          response.statusCode,
        );
      }
    } catch (e) {
      print('Error in deleteConversation: $e');
      if (e is ApiException) rethrow;
      throw ApiException('Network error: $e', 0);
    }
  }

  void dispose() {
    _client.close();
  }
}

class ApiException implements Exception {
  final String message;
  final int statusCode;

  ApiException(this.message, this.statusCode);

  @override
  String toString() => 'ApiException: $message (Status: $statusCode)';
}
