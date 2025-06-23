import 'package:flutter/foundation.dart';
import '../models/message.dart';
import '../models/conversation.dart';
import '../services/api_service.dart';

class ChatProvider with ChangeNotifier {
  final ApiService _apiService = ApiService();
  
  List<ConversationSummary> _conversations = [];
  Conversation? _currentConversation;
  List<Message> _messages = [];
  bool _isLoading = false;
  bool _isSending = false;
  String? _error;

  List<ConversationSummary> get conversations => _conversations;
  Conversation? get currentConversation => _currentConversation;
  List<Message> get messages => _messages;
  bool get isLoading => _isLoading;
  bool get isSending => _isSending;
  String? get error => _error;

  Future<void> loadConversations() async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      _conversations = await _apiService.getConversations();
    } catch (e) {
      _error = e.toString();
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> loadConversation(String conversationId) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      _currentConversation = await _apiService.getConversation(conversationId);
      _messages = _currentConversation!.messages;
    } catch (e) {
      _error = e.toString();
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> createNewConversation() async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final newConversation = await _apiService.createConversation();
      _conversations.insert(0, newConversation);
      _currentConversation = Conversation(
        id: newConversation.id,
        title: newConversation.title,
        createdAt: newConversation.createdAt,
        messages: [],
        messageCount: 0,
      );
      _messages = [];
    } catch (e) {
      _error = e.toString();
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> deleteConversation(String conversationId) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      await _apiService.deleteConversation(conversationId);
      
      // Remove from local list
      _conversations.removeWhere((conv) => conv.id == conversationId);
      
      // Clear current conversation if it was deleted
      if (_currentConversation?.id == conversationId) {
        _currentConversation = null;
        _messages = [];
      }
    } catch (e) {
      _error = e.toString();
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> sendMessage(String messageText) async {
    if (messageText.trim().isEmpty || _isSending) return;

    _isSending = true;
    _error = null;
    
    // Add user message immediately to UI
    final userMessage = Message(
      id: DateTime.now().millisecondsSinceEpoch.toString(),
      role: 'user',
      content: messageText,
      createdAt: DateTime.now(),
    );
    _messages.add(userMessage);
    notifyListeners();

    try {
      final request = MessageRequest(
        conversationId: _currentConversation?.id,
        message: messageText,
      );

      final response = await _apiService.sendMessage(request);
      
      // Update conversation ID if it's a new conversation
      if (_currentConversation?.id != response.conversationId) {
        _currentConversation = _currentConversation?.copyWith(
          id: response.conversationId ?? _currentConversation!.id,
        );
      }

      // Add assistant message
      if (response.content != null) {
        final assistantMessage = Message(
          id: response.id,
          role: 'assistant',
          content: response.content!,
          createdAt: DateTime.fromMillisecondsSinceEpoch(response.created * 1000),
        );
        _messages.add(assistantMessage);
      }
    } catch (e) {
      _error = e.toString();
      // Remove the user message if sending failed
      _messages.removeLast();
    } finally {
      _isSending = false;
      notifyListeners();
    }
  }

  void clearError() {
    _error = null;
    notifyListeners();
  }

  void clearCurrentConversation() {
    _currentConversation = null;
    _messages = [];
    notifyListeners();
  }

  @override
  void dispose() {
    _apiService.dispose();
    super.dispose();
  }
}
