export function getRouteByNotification(notificationType, authorId) {
  switch (notificationType) {
    case 'MESSAGE':
      return { name: 'Im', params: { id: authorId } };
    default:
      return {
        name: 'ProfileId',
        params: { id: authorId },
      };
  }
}
