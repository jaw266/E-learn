export function Logo({ compact = false }: { compact?: boolean }) {
  return (
    <div className="logo-wrap" aria-label="E-Learn Pro logo">
      <svg width="42" height="42" viewBox="0 0 64 64" fill="none" role="img">
        <rect x="7" y="8" width="50" height="50" rx="15" fill="url(#g)" />
        <path d="M18 20.5C18 18.6 19.6 17 21.5 17H33C36.9 17 40 20.1 40 24V47H25C21.1 47 18 43.9 18 40V20.5Z" fill="white" fillOpacity="0.95" />
        <path d="M32 17H42.5C44.4 17 46 18.6 46 20.5V40C46 43.9 42.9 47 39 47H32V17Z" fill="#D7E9FF" />
        <path d="M24 26H34M24 32H34M37 26H42M37 32H42" stroke="#1769E0" strokeWidth="2.6" strokeLinecap="round" />
        <defs>
          <linearGradient id="g" x1="8" y1="8" x2="56" y2="58" gradientUnits="userSpaceOnUse">
            <stop stopColor="#0EA5E9" />
            <stop offset="1" stopColor="#1D4ED8" />
          </linearGradient>
        </defs>
      </svg>
      {!compact && (
        <div>
          <strong>E-Learn Pro</strong>
          <span>Smart learning library</span>
        </div>
      )}
    </div>
  );
}
